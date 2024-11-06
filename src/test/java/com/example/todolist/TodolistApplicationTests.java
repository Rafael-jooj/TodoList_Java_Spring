package com.example.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.todolist.entity.Todo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TodolistApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testCreateTodoSuccess() {
		var todo = new Todo("Todo test", "desc test", 1, false);

		webTestClient
			.post()
			.uri("/todos")
			.bodyValue(todo)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(1)
			.jsonPath("$[0].nome").isEqualTo(todo.getNome())
			.jsonPath("$[0].descricao").isEqualTo(todo.getDescricao())
			.jsonPath("$[0].prioridade").isEqualTo(todo.getPrioridade())
			.jsonPath("$[0].realizado").isEqualTo(todo.getRealizado());
	}

	@Test
	void testCreateTodoFailure() {
		webTestClient
			.post()
			.uri("/todos")
			.bodyValue(new Todo("", "", 0, false))
			.exchange()
			.expectStatus().isBadRequest();
	}

}
