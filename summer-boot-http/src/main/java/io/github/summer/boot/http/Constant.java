package io.github.summer.boot.http;

import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;

import java.nio.charset.Charset;

/**
 * 常量
 *
 * @author changebooks@qq.com
 */
public interface Constant {
    /**
     * 支持的字符编码
     */
    Charset CHARSET = Consts.UTF_8;

    /**
     * 普通协议
     */
    String SCHEME_HTTP = "http";

    /**
     * 加密协议
     */
    String SCHEME_HTTPS = "https";

    /**
     * 协议分隔符
     */
    String SCHEME_SEPARATOR = "://";

    /**
     * 普通端口
     */
    int PORT_HTTP = 80;

    /**
     * 加密端口
     */
    int PORT_HTTPS = 443;

    /**
     * 端口分隔符
     */
    String PORT_SEPARATOR = ":";

    /**
     * Get
     */
    String METHOD_GET = HttpGet.METHOD_NAME;

    /**
     * Post
     */
    String METHOD_POST = HttpPost.METHOD_NAME;

    /**
     * Head
     */
    String METHOD_HEAD = HttpHead.METHOD_NAME;

    /**
     * Options
     */
    String METHOD_OPTIONS = HttpOptions.METHOD_NAME;

    /**
     * Put
     */
    String METHOD_PUT = HttpPut.METHOD_NAME;

    /**
     * Delete
     */
    String METHOD_DELETE = HttpDelete.METHOD_NAME;

    /**
     * Trace
     */
    String METHOD_TRACE = HttpTrace.METHOD_NAME;

    /**
     * Connect
     */
    String METHOD_CONNECT = "CONNECT";

    /**
     * Link
     */
    String METHOD_LINK = "LINK";

    /**
     * Unlink
     */
    String METHOD_UNLINK = "UNLINK";

    /**
     * Patch
     */
    String METHOD_PATCH = "PATCH";

    /**
     * Copy
     */
    String METHOD_COPY = "COPY";

    /**
     * Wrapped
     */
    String METHOD_WRAPPED = "WRAPPED";

    /**
     * Extension Method
     */
    String METHOD_EXTENSION = "EXTENSION-METHOD";

    /**
     * Continue (HTTP/1.1 - RFC 2616)
     */
    int SC_CONTINUE = HttpStatus.SC_CONTINUE;

    /**
     * Switching Protocols (HTTP/1.1 - RFC 2616)
     */
    int SC_SWITCHING_PROTOCOLS = HttpStatus.SC_SWITCHING_PROTOCOLS;

    /**
     * Processing (WebDAV - RFC 2518)
     */
    int SC_PROCESSING = HttpStatus.SC_PROCESSING;

    /**
     * OK (HTTP/1.0 - RFC 1945)
     */
    int SC_OK = HttpStatus.SC_OK;

    /**
     * Created (HTTP/1.0 - RFC 1945)
     */
    int SC_CREATED = HttpStatus.SC_CREATED;

    /**
     * Accepted (HTTP/1.0 - RFC 1945)
     */
    int SC_ACCEPTED = HttpStatus.SC_ACCEPTED;

    /**
     * Non Authoritative Information (HTTP/1.1 - RFC 2616)
     */
    int SC_NON_AUTHORITATIVE_INFORMATION = HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION;

    /**
     * No Content (HTTP/1.0 - RFC 1945)
     */
    int SC_NO_CONTENT = HttpStatus.SC_NO_CONTENT;

    /**
     * Reset Content (HTTP/1.1 - RFC 2616)
     */
    int SC_RESET_CONTENT = HttpStatus.SC_RESET_CONTENT;

    /**
     * Partial Content (HTTP/1.1 - RFC 2616)
     */
    int SC_PARTIAL_CONTENT = HttpStatus.SC_PARTIAL_CONTENT;

    /**
     * Multi-Status (WebDAV - RFC 2518) or 207 Partial Update OK (HTTP/1.1 - draft-ietf-http-v11-spec-rev-01?)
     */
    int SC_MULTI_STATUS = HttpStatus.SC_MULTI_STATUS;

    /**
     * Multiple Choices (HTTP/1.1 - RFC 2616)
     */
    int SC_MULTIPLE_CHOICES = HttpStatus.SC_MULTIPLE_CHOICES;

    /**
     * Moved Permanently (HTTP/1.0 - RFC 1945)
     */
    int SC_MOVED_PERMANENTLY = HttpStatus.SC_MOVED_PERMANENTLY;

    /**
     * Moved Temporarily (Sometimes Found) (HTTP/1.0 - RFC 1945)
     */
    int SC_MOVED_TEMPORARILY = HttpStatus.SC_MOVED_TEMPORARILY;

    /**
     * See Other (HTTP/1.1 - RFC 2616)
     */
    int SC_SEE_OTHER = HttpStatus.SC_SEE_OTHER;

    /**
     * Not Modified (HTTP/1.0 - RFC 1945)
     */
    int SC_NOT_MODIFIED = HttpStatus.SC_NOT_MODIFIED;

    /**
     * Use Proxy (HTTP/1.1 - RFC 2616)
     */
    int SC_USE_PROXY = HttpStatus.SC_USE_PROXY;

    /**
     * Temporary Redirect (HTTP/1.1 - RFC 2616)
     */
    int SC_TEMPORARY_REDIRECT = HttpStatus.SC_TEMPORARY_REDIRECT;

    /**
     * Bad Request (HTTP/1.1 - RFC 2616)
     */
    int SC_BAD_REQUEST = HttpStatus.SC_BAD_REQUEST;

    /**
     * Unauthorized (HTTP/1.0 - RFC 1945)
     */
    int SC_UNAUTHORIZED = HttpStatus.SC_UNAUTHORIZED;

    /**
     * Payment Required (HTTP/1.1 - RFC 2616)
     */
    int SC_PAYMENT_REQUIRED = HttpStatus.SC_PAYMENT_REQUIRED;

    /**
     * Forbidden (HTTP/1.0 - RFC 1945)
     */
    int SC_FORBIDDEN = HttpStatus.SC_FORBIDDEN;

    /**
     * Not Found (HTTP/1.0 - RFC 1945)
     */
    int SC_NOT_FOUND = HttpStatus.SC_NOT_FOUND;

    /**
     * Method Not Allowed (HTTP/1.1 - RFC 2616)
     */
    int SC_METHOD_NOT_ALLOWED = HttpStatus.SC_METHOD_NOT_ALLOWED;

    /**
     * Not Acceptable (HTTP/1.1 - RFC 2616)
     */
    int SC_NOT_ACCEPTABLE = HttpStatus.SC_NOT_ACCEPTABLE;

    /**
     * Proxy Authentication Required (HTTP/1.1 - RFC 2616)
     */
    int SC_PROXY_AUTHENTICATION_REQUIRED = HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED;

    /**
     * Request Timeout (HTTP/1.1 - RFC 2616)
     */
    int SC_REQUEST_TIMEOUT = HttpStatus.SC_REQUEST_TIMEOUT;

    /**
     * Conflict (HTTP/1.1 - RFC 2616)
     */
    int SC_CONFLICT = HttpStatus.SC_CONFLICT;

    /**
     * Gone (HTTP/1.1 - RFC 2616)
     */
    int SC_GONE = HttpStatus.SC_GONE;

    /**
     * Length Required (HTTP/1.1 - RFC 2616)
     */
    int SC_LENGTH_REQUIRED = HttpStatus.SC_LENGTH_REQUIRED;

    /**
     * Precondition Failed (HTTP/1.1 - RFC 2616)
     */
    int SC_PRECONDITION_FAILED = HttpStatus.SC_PRECONDITION_FAILED;

    /**
     * Request Entity Too Large (HTTP/1.1 - RFC 2616)
     */
    int SC_REQUEST_TOO_LONG = HttpStatus.SC_REQUEST_TOO_LONG;

    /**
     * Request-URI Too Long (HTTP/1.1 - RFC 2616)
     */
    int SC_REQUEST_URI_TOO_LONG = HttpStatus.SC_REQUEST_URI_TOO_LONG;

    /**
     * Unsupported Media Type (HTTP/1.1 - RFC 2616)
     */
    int SC_UNSUPPORTED_MEDIA_TYPE = HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE;

    /**
     * Requested Range Not Satisfiable (HTTP/1.1 - RFC 2616)
     */
    int SC_REQUESTED_RANGE_NOT_SATISFIABLE = HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE;

    /**
     * Expectation Failed (HTTP/1.1 - RFC 2616)
     */
    int SC_EXPECTATION_FAILED = HttpStatus.SC_EXPECTATION_FAILED;

    /**
     * Static constant for a 419 error.
     */
    int SC_INSUFFICIENT_SPACE_ON_RESOURCE = HttpStatus.SC_INSUFFICIENT_SPACE_ON_RESOURCE;

    /**
     * Static constant for a 420 error.
     */
    int SC_METHOD_FAILURE = HttpStatus.SC_METHOD_FAILURE;

    /**
     * Unprocessable Entity (WebDAV - RFC 2518)
     */
    int SC_UNPROCESSABLE_ENTITY = HttpStatus.SC_UNPROCESSABLE_ENTITY;

    /**
     * Locked (WebDAV - RFC 2518)
     */
    int SC_LOCKED = HttpStatus.SC_LOCKED;

    /**
     * Failed Dependency (WebDAV - RFC 2518)
     */
    int SC_FAILED_DEPENDENCY = HttpStatus.SC_FAILED_DEPENDENCY;

    /**
     * Server Error (HTTP/1.0 - RFC 1945)
     */
    int SC_INTERNAL_SERVER_ERROR = HttpStatus.SC_INTERNAL_SERVER_ERROR;

    /**
     * Not Implemented (HTTP/1.0 - RFC 1945)
     */
    int SC_NOT_IMPLEMENTED = HttpStatus.SC_NOT_IMPLEMENTED;

    /**
     * Bad Gateway (HTTP/1.0 - RFC 1945)
     */
    int SC_BAD_GATEWAY = HttpStatus.SC_BAD_GATEWAY;

    /**
     * Service Unavailable (HTTP/1.0 - RFC 1945)
     */
    int SC_SERVICE_UNAVAILABLE = HttpStatus.SC_SERVICE_UNAVAILABLE;

    /**
     * Gateway Timeout (HTTP/1.1 - RFC 2616)
     */
    int SC_GATEWAY_TIMEOUT = HttpStatus.SC_GATEWAY_TIMEOUT;

    /**
     * HTTP Version Not Supported (HTTP/1.1 - RFC 2616)
     */
    int SC_HTTP_VERSION_NOT_SUPPORTED = HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED;

    /**
     * Insufficient Storage (WebDAV - RFC 2518)
     */
    int SC_INSUFFICIENT_STORAGE = HttpStatus.SC_INSUFFICIENT_STORAGE;

}
