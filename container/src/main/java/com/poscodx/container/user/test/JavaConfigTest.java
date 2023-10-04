package com.poscodx.container.user.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaConfigTest {

	public static void main(String[] args) {
		testJavaConfitTest();
	}

	private static void testJavaConfitTest() {
		new AnnotationConfigApplicationContext(AppConfig.class);
	}

}
