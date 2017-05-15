package it.er.outweb;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import it.er.dao.IParamDAO;
import java.sql.SQLException;
import it.er.util.SingletonLookup;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ScarabOutWebServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 241805341489492477L;

	public void init(){
		System.out.println("Init out  web servlet, Scarab");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res){
		InputStream in = null;
		OutputStream out = null;
		String fileName = req.getParameter("webfile");
		byte[] err = "File not found or error in folder".getBytes();
		IParamDAO param = SingletonLookup.getParamDAO(req.getServletContext());
		try {
			if (fileName != null && !fileName.isEmpty()){
				in = new FileInputStream(param.getParam("WebContentRoot").getValue()+File.separator+fileName);
				byte[] b = new byte[8];
				int len = 0;
				out = res.getOutputStream();
				while (len > -1){
					len = in.read(b);
					out.write(b, 0, len);
					out.flush();					
				}
			} else {
				out = res.getOutputStream();
				out.write(err);
				out.flush();
			}
		} catch (FileNotFoundException e) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (in != null){
					in.close();
				}
				if (out != null){
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void destroy(){
		System.out.println("Destroy out  web servlet, Scarab");
	}
}

