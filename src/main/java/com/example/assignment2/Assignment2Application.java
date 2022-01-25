package com.example.assignment2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
TODO
1) no need for ResponseEntity
2) query param, log4j, request params
3) junit testing for controller and functionalities , make more applications, take  data from different api
4)  start some courses : aws basics and devops tools with dockers(docker and microservices)
	container approach -> kubernetes/docker
	build -> image -> push to github -> ec2 container-> server pulls from dockerhub (no need for jar files)
	(devops part)

	aws -> ecs and ecr containers, s3 buckets with java, queue and messaging throughput, retention, lambda triggers
	dynamodb -> modification and altering

	dockerhub -> start create acc and take overview for pushing image and pulling , running on docker.
*/

@SpringBootApplication
public class Assignment2Application {

	public static void main(String[] args) {
		SpringApplication.run(Assignment2Application.class, args);
	}

}
