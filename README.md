# final.project
This is my final project of "enrich Java skill program"


# Requirements
Viết 1 chương trình bằng Java có các tính năng sau (lưu ý: bạn có thể làm font-end hoặc có thể viết các APIs để hoàn thành các features):        

1. Cho phép người dùng nhập và lưu dữ liệu về khóa học bao gồm các thông tin:       
- Tên khóa học,       
- Ngày bắt đầu,       
- Ngày kết thúc,       
- Hiển thị thông báo nếu người dùng nhập sai hoặc thiếu thông tin.

2. Cho phép người dùng nhập và lưu dữ liệu về người học bao gồm các thông tin:       
-  Tên người học,      
-  Ngày sinh,       
-  Địa chỉ.       
-  Hiển thị thông báo nếu người dùng nhập sai hoặc thiếu thông tin. 
Người học có thể tham gia bất kỳ khóa học nào nếu khóa học đó chưa diễn ra.       

3. Cho phép người dùng import data từ CSV file về khóa học. (
 - Hiển thị thông báo nếu người dùng import sai hoặc thiếu thông tin.    

4. 
Hiển thị danh sách top 10 khóa học có kết quả tốt nhất với người học. (Ko rõ yêu cầu)
Hiển thị thông báo nếu không tìm thấy thông tin.    

5. Cho phép người dùng tìm kiếm khóa học theo tên và hiển thị danh sách theo thứ tự alphabet nếu có. 
Hiển thị thông báo nếu không tìm thấy thông tin.   

6. Cho phép người dùng cập nhật thông tin về khóa học nếu khóa học chưa diễn ra. Hiển thị thông báo lỗi nếu có   (Admin theo ID)

7. Cho phép người dùng xóa 1 hoặc nhiều khóa học. Nếu khóa học đang diễn ra thì người dùng không dc phép xóa.    

8. Cho phép lưu tất cả hành động của người dùng ra file có tên activities.log (lưu gì?)

# Databases configuration
--- JPA Configuration for MySQL database---
debug=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://localhost:3306/springweb?serverTimezone=UTC

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update

----- JPA configuration for H2-Console ---
# Enabling H2 Console
spring.h2.console.enabled=true
#turn statictis on
spring.jpa.properties.hibernate.generate_statistics=true
logging.level.org.hibernate.stat=debug
#show all queries
spring.jpa.show-sql=true
logging.level.org.hibernate.type=trace
spring.jpa.properties.hibernate.format_sql=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.defer-datasource-initialization=true
# For spring3.0
#spring.data.jpa.repositories.bootstrap-mode=default
----
