#### 系统主入口

##### 数据库版本管理 
                <plugin>
                    <groupId>org.flywaydb</groupId>
                    <artifactId>flyway-maven-plugin</artifactId>
                    <version>4.2.0</version>
                    <configuration>
                        <url>jdbc:mysql://localhost:3306/faker?serverTimezone=UTC&amp;useUnicode=true&amp;characterEncoding=utf-8&amp;useSSL=false</url>
                        <user>root</user>
                        <password>123456</password>
                    </configuration>
                    <dependencies>
                        <dependency>
                            <groupId>mysql</groupId>
                            <artifactId>mysql-connector-java</artifactId>
                            <version>5.1.41</version>
                        </dependency>
                    </dependencies>
                </plugin>