package com.dong.server.config.viewresolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.dong.server.config.util.JsonUtil;

public class JsonViewResolver implements ViewResolver{

	@Override
	public View resolveViewName(String arg0, Locale arg1) throws Exception {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setPrettyPrint(true);
//		/view.setObjectMapper(JsonUtil.objMapper);
		return view;
	}

}
