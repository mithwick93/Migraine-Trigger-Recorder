CREATE TABLE activity(

  activity_id INT,
  activity_name VARCHAR(250) UNIQUE NOT NULL,
  priority INT UNIQUE NOT NULL,
  
  PRIMARY KEY (activity_id)
)engine=INNODB;

CREATE TABLE body_area(

  area_id INT,
  area_name VARCHAR(250) UNIQUE NOT NULL,
  
  PRIMARY KEY (area_id)
)engine=INNODB;

CREATE TABLE location(

  location_id INT,
  location_name VARCHAR(250) UNIQUE NOT NULL,
  
  PRIMARY KEY (location_id)
)engine=INNODB;

CREATE TABLE medicine(

  medicine_id INT,
  medicine_name VARCHAR(250) UNIQUE NOT NULL,
  priority INT UNIQUE NOT NULL,
  
  PRIMARY KEY (medicine_id)
)engine=INNODB;

CREATE TABLE migraine_record(

  record_id INT,
  start_time INT DEFAULT NULL,
  end_time INT DEFAULT NULL,
  intensity INT DEFAULT NULL,
  
  PRIMARY KEY (record_id)  
)engine=INNODB;

CREATE TABLE relief(

  relief_id INT,
  relief_name VARCHAR(250) UNIQUE NOT NULL,
  priority INT UNIQUE NOT NULL,
  
  PRIMARY KEY (relief_id)
)engine=INNODB;

CREATE TABLE symptom(

  symptom_id INT,
  symptom_name VARCHAR(250) UNIQUE NOT NULL,
  priority INT UNIQUE NOT NULL,
  
  PRIMARY KEY (symptom_id)
)engine=INNODB;

CREATE TABLE migraine_trigger(

  trigger_id INT,
  trigger_name VARCHAR(250) UNIQUE NOT NULL,
  priority INT UNIQUE NOT NULL,
  
  PRIMARY KEY (trigger_id)
)engine=INNODB;

CREATE TABLE weather_data(

  weather_id INT,
  record_id INT,
  humidity DOUBLE DEFAULT NULL,
  pressure DOUBLE DEFAULT NULL,
  temperature DOUBLE DEFAULT NULL,
  
  PRIMARY KEY (weather_id,record_id),
  CONSTRAINT fk_weather_data_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE 
)engine=INNODB;

CREATE TABLE activity_record(

  activity_id INT,
  record_id INT,  
  
  PRIMARY KEY (activity_id,record_id),
  CONSTRAINT fk_activity_record_activity_id FOREIGN KEY (activity_id) REFERENCES activity(activity_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_activity_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE 
)engine=INNODB;

CREATE TABLE body_area_record(

  area_id INT,
  record_id INT,
  
  PRIMARY KEY (area_id,record_id),
  CONSTRAINT fk_body_area_record_area_id FOREIGN KEY (area_id) REFERENCES body_area(area_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_body_area_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE 
)engine=INNODB;

CREATE TABLE location_record(

  location_id INT,
  record_id INT,
  
  PRIMARY KEY (location_id,record_id),
  CONSTRAINT fk__location_record_location_id FOREIGN KEY (location_id) REFERENCES location(location_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_location_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE 
)engine=INNODB;

CREATE TABLE medicine_record(

  medicine_id INT,
  record_id INT, 
  effective VARCHAR(250) NOT NULL DEFAULT 'f',
  
  PRIMARY KEY (medicine_id,record_id),
  CONSTRAINT fk_medicine_record_medicine_id FOREIGN KEY (medicine_id) REFERENCES medicine(medicine_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_medicine_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE 
)engine=INNODB;

CREATE TABLE relief_record(
 
  relief_id INT,
  record_id INT,
  effective VARCHAR(250) NOT NULL DEFAULT 'f',
  
  PRIMARY KEY (relief_id,record_id),
  CONSTRAINT fk_relief_record_relief_id FOREIGN KEY (relief_id) REFERENCES relief(relief_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_relief_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE 
)engine=INNODB;

CREATE TABLE symptom_record(
 
  symptom_id INT,
  record_id INT,
  
  PRIMARY KEY (symptom_id,record_id),
  CONSTRAINT fk_symptom_record_symptom_id FOREIGN KEY (symptom_id) REFERENCES symptom(symptom_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_symptom_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE 
)engine=INNODB;

CREATE TABLE trigger_record(
  
  trigger_id INT,
  record_id INT,
  
  PRIMARY KEY (trigger_id,record_id),
  CONSTRAINT fk_trigger_record_trigger_id FOREIGN KEY (trigger_id) REFERENCES migraine_trigger(trigger_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_trigger_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE 
)engine=INNODB;