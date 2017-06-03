package com.lzj.control;

import com.lzj.domain.WebEntity;
import com.lzj.utils.WebSearch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
@WebServlet("/searchAction")
public class SearchAction  extends HttpServlet{
    private WebSearch webSearch;
    @Override
    public void init() throws ServletException {
        webSearch=new WebSearch();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String key=req.getParameter("weburl");
        List<WebEntity>list=webSearch.listWebEntities(key);
        req.setAttribute("list",list);
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }
}
