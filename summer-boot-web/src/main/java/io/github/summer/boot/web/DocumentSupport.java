package io.github.summer.boot.web;

import io.github.summer.boot.autoconfigure.DocumentProperties;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import java.util.Optional;

/**
 * 通过默认的方法，或重写的子方法，创建实例
 *
 * @author changebooks@qq.com
 */
public class DocumentSupport {
    /**
     * 接口信息
     *
     * @param documentProperties {@link DocumentProperties} 配置
     * @return {@link Info} 实例
     */
    public Info info(DocumentProperties documentProperties) {
        documentProperties = Optional.ofNullable(documentProperties).orElse(defaultProperties());

        Info result = new Info();

        String title = documentProperties.getTitle();
        result.setTitle(title);

        String description = documentProperties.getDescription();
        result.setDescription(description);

        Contact contact = contact(documentProperties.getContact());
        result.setContact(contact);

        License license = license(documentProperties.getLicense());
        result.setLicense(license);

        String version = documentProperties.getVersion();
        result.setVersion(version);

        return result;
    }

    /**
     * 默认的配置
     *
     * @return {@link DocumentProperties} 配置
     */
    public DocumentProperties defaultProperties() {
        return new DocumentProperties();
    }

    /**
     * 联系人
     *
     * @param contact {@link DocumentProperties.Contact} 配置
     * @return {@link Contact} 实例
     */
    public Contact contact(DocumentProperties.Contact contact) {
        contact = Optional.ofNullable(contact).orElse(defaultContact());

        Contact result = new Contact();

        String name = contact.getName();
        result.setName(name);

        String url = contact.getUrl();
        result.setUrl(url);

        String email = contact.getEmail();
        result.setEmail(email);

        return result;
    }

    /**
     * 默认的联系人
     *
     * @return {@link DocumentProperties.Contact} 配置
     */
    public DocumentProperties.Contact defaultContact() {
        return new DocumentProperties.Contact();
    }

    /**
     * 许可证
     *
     * @param license {@link DocumentProperties.License} 配置
     * @return {@link License} 实例
     */
    public License license(DocumentProperties.License license) {
        license = Optional.ofNullable(license).orElse(defaultLicense());

        License result = new License();

        String name = license.getName();
        result.setName(name);

        String url = license.getUrl();
        result.setUrl(url);

        return result;
    }

    /**
     * 默认的许可证
     *
     * @return {@link DocumentProperties.License} 配置
     */
    public DocumentProperties.License defaultLicense() {
        return new DocumentProperties.License();
    }

}
