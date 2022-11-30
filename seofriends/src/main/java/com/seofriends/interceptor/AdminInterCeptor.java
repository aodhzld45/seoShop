package com.seofriends.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.seofriends.domain.AdminVO;

//관리자 로그인 인터셉터 
public class AdminInterCeptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		boolean result = false;
		
		//인증된 사용자인지 여부를 체크. 세션 객체를 확인한다.
		HttpSession session = request.getSession();
		//session.getAttribute("로그인시 세션키");
		AdminVO user = (AdminVO) session.getAttribute("adminstatus");
		
		if (user == null) { //인증 정보가 존재하지 않음. -> 비로그인 사용자.
			result = false;
			
			//Ajax 요청인지 여부를 체크한다.
			if (isAjaxRequest(request)) {
//				log.info("ajax 요청");
				System.out.println("ajax 요청");
				response.sendError(400);
				
			}else {
				//log.info("ajax 요청아님");
				System.out.println("ajax 요청아님");
				getDestination(request);
				
//				String uri = request.getRequestURI();
//				if (uri.equals("/admin/adLoginPost")) {
//					return true;
//				}
				response.sendRedirect("/admin/adLogin");
			}
			
		}else { //인증 정보가 존재한다. -> 현재 로그인한 상태
			result = true;
		}
		
		return result; // true이면, Controller로 제어가 넘어간다.
	}
	
	
	//Ajax 요청인지 여부를 체크한다.
	private boolean isAjaxRequest(HttpServletRequest request) {
		
		boolean isAjax = false;
		
		//Ajax 구문에서 요청시 헤더에 Ajax : "true" 를 작업해두어야 한다.
		String header = request.getHeader("AJAX");
		if ("true".equals(header)) {
			isAjax = true;
			return isAjax;
		}
		
		return isAjax;
	}

	private void getDestination(HttpServletRequest request) {
		String uri = request.getRequestURI(); // 브라우저가 요청한 주소. 
		String query = request.getQueryString(); // /prouduct/cart?pdt_num=10;
		
		if (query == null || query.equals("null")) {
			query = "";
		}else {
			query = "?" + query;
		}
		
		String destination = uri + query; //  /prouduct/cart?pdt_num=10 or /product/cart
		
		if (request.getMethod().equals("GET")) {
			//사용자가 비로그인상태에서 요청한 원래주소를 세션으로 지정해준다.
			request.getSession().setAttribute("dest", destination);
		}
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		super.afterCompletion(request, response, handler, ex);
	}

	
}
