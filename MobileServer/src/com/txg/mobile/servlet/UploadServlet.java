package com.txg.mobile.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.txg.mobile.domain.Player;
import com.txg.mobile.service.MarkService;
import com.txg.mobile.utils.ChangeToJSON;


public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public MarkService ms = new MarkService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String data = request.getParameter("data");
		JSONObject dataObject = JSONObject.parseObject(data);
		String mobile = dataObject.getString("mobile");
		String mark = dataObject.getString("mark");
		PrintWriter writer = response.getWriter();
		
		if (ms.findPlayer(mobile)) {
			if (ms.updateMark(mobile, mark)) {
				//First global if friends then add.
				List<Player> list = ms.getGlobalTopMark(10);
				writer.write(ChangeToJSON.playerToJSON(list).toString());
			}else {
				writer.write("Update Fail");
			}
		}else {
			if(ms.insertMark(mobile, mark)) {
				List<Player> list = ms.getGlobalTopMark(10);
				writer.write(ChangeToJSON.playerToJSON(list).toString());
			}else {
				writer.write("Insert Fail");
			}
		}
		
		
	}

}
