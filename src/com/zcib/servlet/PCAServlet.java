package com.zcib.servlet;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import com.zcib.service.PCAService;

@WebServlet("/PCAServlet")
public class PCAServlet extends HttpServlet {
	
	private PCAService pcaService = new PCAService();

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		request.setCharacterEncoding("utf-8");//请求
		response.setContentType("text/html;charset=utf-8");//响应
		
		String action = request.getParameter("action");
		
		if ("getprovinces".equals(action)) {//省份
			getprovinces(request,response);
		}else if ("getcities".equals(action)) {//市
			getcities(request,response);
		}else if ("getareas".equals(action)) {//区
			getareas(request,response);
		}
	}


	//获取新增地址的所在地的省份
	private void getprovinces(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		/**
		 * 1.获取provinces，调用service层的方法实现
		 * 2.将list封装到JSONArray中
		 * 3.返回给页面
		 */
		List<Map<String , Object>> list  = pcaService.getProvinces();
		JSONArray jsonArray = JSONArray.fromObject(list);
		response.getWriter().print(jsonArray.toString());
	}
	
	//获取新增地址的所在地的省份的市列表
	private void getcities(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		/**
		 * 1.获取省级编号
		 * 2.根据省级编号调用service层方法来获取市编号
		 * 3.封装到JSONArray中
		 * 4.发送给页面
		 */
		String provinceid  = request.getParameter("provinceid");
		List<Map<String, Object>> list  = pcaService.getCities(provinceid);
		JSONArray jsonArray = JSONArray.fromObject(list);//使用JSONArray的静态方法
		response.getWriter().print(jsonArray.toString());
	}
	
	
	//获取新增地址的所在地的市区的区列表
	private void getareas(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		/**
		 * 1.获取市级编号
		 * 2.根据市级编号调用service层方法来获取区编号
		 * 3.封装到JSONArray中
		 * 4.发送给页面
		 */
		String cityid  = request.getParameter("cityid");
		List<Map<String, Object>> list  = pcaService.getAreas(cityid);
		JSONArray jsonArray = JSONArray.fromObject(list);//使用JSONArray的静态方法
		response.getWriter().print(jsonArray.toString());
	}
}
