package com.hirain.phm.bd.ground.authority;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

public class RbacTest extends BaseTest {

	@Test
	public void testApprovalRoleController() throws Exception {
		mockMvc.perform(get("/approvalRole/getAllApprovalRoles").session(session)).andExpect(status().isOk())

				.andDo(print());
	}
}
