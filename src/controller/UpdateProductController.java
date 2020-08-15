package controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.bean.Product;
import model.dao.ProductDAO;
import model.dao.UpdateProductDAO;

@WebServlet("/UpdateProduct")
@MultipartConfig
public class UpdateProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DIR_UPLOAD = "imageUpdate";

	public UpdateProductController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		int id=Integer.parseInt(request.getParameter("id"));
		Product objPro=UpdateProductDAO.getItem(id);
		request.setAttribute("objPro",objPro );
		request.getRequestDispatcher("/update.jsp").forward(request, response);
		String tenHoa = request.getParameter("tenHoa");
		String moTa = request.getParameter("moTa");
		String giaBanString = request.getParameter("giaBan");

		request.setAttribute("tenHoa", tenHoa);
		request.setAttribute("moTa", moTa);
		if (tenHoa.equals("")) {
			request.setAttribute("err", "Vui lòng nhập tên hoa!!!");
			request.getRequestDispatcher("/update.jsp").forward(request, response);
		}
		if (moTa.equals("")) {
			request.setAttribute("err", "Vui lòng nhập thông tin mô tả!!!");
			request.getRequestDispatcher("/update.jsp").forward(request, response);
		}
		String fileName = "", dirUpLoad = "", fileReName = "";

		Part filePart = request.getPart("hinhAnh");
		fileName = filePart.getSubmittedFileName();// lấy tên thư mục gốc

		try {
			if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".jpeg")
					&& !fileName.endsWith(".gif")) {
				throw new Exception();
			}
			String contextRoot = request.getServletContext().getRealPath("");
			String dirUpload = contextRoot + DIR_UPLOAD;
			File saveDir = new File(dirUpload);
			if (!saveDir.exists()) {
				saveDir.mkdir();
			}

			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
			String fname = sdf.format(date);
			fileName = "images_" + fname + ".jpg";

			String filePath = dirUpload + File.separator + fileName;
			filePart.write(filePath);
		} catch (Exception e) {
			request.setAttribute("err", "Vui lòng chọn file hình ảnh!!!");
			request.getRequestDispatcher("/update.jsp").forward(request, response);
			return;
		}

		if (giaBanString.equals("")) {
			request.setAttribute("err", "Vui lòng nhập thông tin giá bán!!!");
			request.getRequestDispatcher("/update.jsp").forward(request, response);
		}
		int giaBan = 0;
		try {
			giaBan = Integer.parseInt(request.getParameter("giaBan"));

		} catch (NumberFormatException e) {
			request.setAttribute("err", "Vui lòng nhập thông tin giá bán là số!!!");
			request.getRequestDispatcher("/update.jsp").forward(request, response);
		}
		request.setAttribute("giaBan", giaBan);

		if (UpdateProductDAO.Updateitems(objPro) > 0) {
			request.setAttribute("msg", "Sửa sản phẩm thành công!");
			request.getRequestDispatcher("/update.jsp").forward(request, response);
		} else {
			request.setAttribute("msg", "Sửa sản phẩm thất bại!");
			request.getRequestDispatcher("/update.jsp").forward(request, response);
		}

	}

}
