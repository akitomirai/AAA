package com.tiankong.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import org.apache.ibatis.type.*;
import java.io.IOException;
import java.sql.*;
import java.util.Collection;
import java.util.List;

@MappedTypes(value = {
        List.class,
        String[].class,  // 明确支持的类型
        Long[].class
})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final JavaType javaType;

    public JsonTypeHandler(Class<T> type) {
        this.javaType = mapper.getTypeFactory().constructType(type);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        try {
            if (parameter == null) {
                ps.setNull(i, Types.VARCHAR);
                return;
            }
            // 处理空集合
            if (parameter instanceof Collection && ((Collection<?>) parameter).isEmpty()) {
                ps.setString(i, "[]");
                return;
            }
            // 正常序列化
            ps.setString(i, mapper.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("JSON序列化失败", e);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return parseJson(json);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJson(rs.getString(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJson(cs.getString(columnIndex));
    }

    private T parseJson(String json) {
        if (StringUtils.isEmpty(json)) return null;
        try {
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new IllegalArgumentException("JSON解析失败: " + json, e);
        }
    }
}