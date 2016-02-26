ALTER TABLE feeds_member_post_fandom ADD post_level varchar(10);
ALTER TABLE connects_member_profile ADD country varchar(10) comment '国别';
ALTER TABLE feeds_fandom ADD level varchar(10) comment '级别';
CREATE TABLE t_pub_code (
  id INTEGER AUTO_INCREMENT COMMENT '主键',
  code_type VARCHAR(10) COMMENT '代码类别',
  code_name VARCHAR(20) COMMENT '代码名称',
  code_value VARCHAR(10) COMMENT '代码值',
  display_order INTEGER DEFAULT 0,
  PRIMARY KEY (id)
);