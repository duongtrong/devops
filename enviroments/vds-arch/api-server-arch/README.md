
## 1. Cài đặt archtype tương ứng với project mẫu

```bash
cd parent-pom/java-spring-parent-pom
mvn clean install
```

```bash
cd api-server-arch
mvn archetype:create-from-project
cd target/generated-sources/archetype
mvn clean install
```

Tương tự với các project khác

## Sử dụng:

Việc sử dụng tùy thuộc vào IDE đang sử dụng.

- NetBeans: 
	
	- Sau khi thực hiện xong bước Cài đặt. Khởi động netbeans. đợi netbean index lại toàn bộ repository
	- Chọn New Project/Maven/Project from Archtype
	- Chọn tên Archtype tương ứng. 
	- Điền thông tin và next đến cuối

- IntelliJ:

	- Cài đặt và cấu hình plug-in Maven Archtype Catalogs và làm theo hướng dẫn tại đây [tại đây](https://plugins.jetbrains.com/plugin/7965-maven-archetype-catalogs)
	- Tạo project Maven, click vào `create from archtype`.
	- Chọn archtype tương ứng.
	- Điền thông tin, next đến cuối

- Không dùng IDE:

```bash
mvn archetype:generate                                  \
  -DarchetypeGroupId=<archetype-groupId>                \
  -DarchetypeArtifactId=<archetype-artifactId>          \
  -DarchetypeVersion=<archetype-version>                \
  -DgroupId=<my.groupid>                                \
  -DartifactId=<my-artifactId>
```

## 2. Thư mục đường dẫn
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── test
│   │   │           └── sampleapp
│   │   │               ├── aop => Các Aspect Oriented Programming rules 
│   │   │               ├── config => Quản lý cấu hình: VD. Cấu hình database, cấu hình kết nối webservice v..v
│   │   │               │   ├── database (support 2 datasource: primary, optional)
│   │   │               │   ├── resttemplate
│   │   │               │   └── vault
│   │   │               ├── constant => Constant. VD: Mã lỗi.
│   │   │               ├── controller => Tầng xử lý API chính
│   │   │               │   ├── api => Interface API
│   │   │               │   └── impl => Implementation của các API
│   │   │               ├── exception => Danh sách các Exception
│   │   │               ├── factory => Các factory VD. Response factory lưu ở đây
│   │   │               │   └── response
│   │   │               ├── filter => Các filter trigger khi có request gửi tới hoặc khi response trả về
│   │   │               ├── locale => Support đa ngôn ngữ
│   │   │               ├── model => Model 
│   │   │               │   ├── converter => Các class sử dụng để chuyển đổi entity attribute state thành database column representation và ngược lại
│   │   │               │   ├── entity => Các database entity của hệ thống
│   │   │               │   └── mapper => Mapper giữa entity và dto
│   │   │               ├── repository => Lớp xử lý dữ liệu 
│   │   │               │   ├── secondary => Support multi datasoure, mỗi package tương ứng với 1 database
│   │   │               │   └── main
│   │   │               ├── service => Lớp cung cấp các service con cho ứng dụng (VD. Service sử dụng các functions trong repository để xử lý nghiệp vụ).
|   |   |               ├── health => Healthcheck service indicator
|   |   |               ├── metrics => Custom metrics (default: jvm, process, cache)
│   │   │               └── util => Lớp helper, cung cấp các hàm util, chú ý. Sử dụng Spring Utils library thay vì tự viết các hàm util: VD. xử lý String v..v 
│   │   └── resources
│   └── test
│       ├── java
│       └── resources
```

## 3. Cấu hình Vault trong file bootstrap.properties
`spring.cloud.vault.host=` hostname của máy chủ Vault

`spring.cloud.vault.port=` port của máy chủ Vault

`spring.cloud.vault.scheme=` scheme là `http` hoặc `https`

`spring.cloud.vault.authentication=` phương thức xác thực có thể là `token`, `appid`, `approle`, ...

`spring.cloud.vault.token=` token để sử dụng máy chủ Valt với phương thức xác thực là token

`spring.cloud.vault.generic.enabled=` kích hoạt hoặc vô hiệu hóa secret backend key-value store (`true`, `false`)

`spring.cloud.vault.generic.backend=` path của key-value store

`spring.cloud.vault.database.enabled=` kích hoạt hoặc vô hiệu hóa database backend (`true`, `false`)

`spring.cloud.vault.database.backend=` sets the path of the database mount to use (`database`, `mysql`, `postgresql`)

`spring.cloud.vault.database.role=` role name of the database role definition

Ngoài các config trên còn những config khác có thể cần config tham khảo tại `https://cloud.spring.io/spring-cloud-vault/reference/html/`

Ví dụ:
```
 spring.cloud.vault.enabled=true
 spring.cloud.vault.authentication=token
 spring.cloud.vault.token=s.TavsJDUrMlXAyk2Bhe2MWglU
 spring.cloud.vault.host=localhost
 spring.cloud.vault.port=8200
 spring.cloud.vault.scheme=http
 spring.cloud.vault.fail-fast=true
 spring.cloud.vault.config.lifecycle.enabled=true
 spring.cloud.vault.generic.enabled=true
 spring.cloud.vault.generic.backend=secret
 spring.cloud.vault.database.enabled=true
 spring.cloud.vault.database.role=order
 spring.cloud.vault.database.backend=database
```

## 4. Cấu hình config client trong file bootstrap.properties
`spring.profiles.active=` tên profile cấu hình muốn lấy về

`spring.cloud.config.label=` (đối với lấy cấu hình từ bitbucket) là branch của cấu hình trên bitbucket

`spring.cloud.config.uri=` địa chỉ của config server

`spring.cloud.config.token=` `role-id`@`secret-id`
