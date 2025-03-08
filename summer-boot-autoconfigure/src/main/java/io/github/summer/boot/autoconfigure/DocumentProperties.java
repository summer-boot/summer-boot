package io.github.summer.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Api文档配置
 *
 * @author changebooks@qq.com
 */
@ConfigurationProperties(prefix = "document")
public class DocumentProperties {
    /**
     * 联系人
     */
    private final Contact contact = new Contact();

    /**
     * 许可证
     */
    private final License license = new License();

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 版本号
     */
    private String version;

    public Contact getContact() {
        return contact;
    }

    public License getLicense() {
        return license;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 联系人
     */
    public static class Contact {
        /**
         * 姓名
         */
        private String name;

        /**
         * 链接
         */
        private String url;

        /**
         * 邮箱
         */
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

    /**
     * 许可证
     */
    public static class License {
        /**
         * 名称
         */
        private String name;

        /**
         * 链接
         */
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

}
