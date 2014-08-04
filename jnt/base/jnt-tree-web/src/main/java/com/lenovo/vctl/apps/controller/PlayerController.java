package com.lenovo.vctl.apps.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PlayerController {
	private static final Log log = LogFactory.getLog(PlayerController.class);

	/**
	 * 玩家登入
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/memory")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model, String name,
			String password) throws Exception {
        model.put("name","arthur");
		return "simple1";
	}


}
