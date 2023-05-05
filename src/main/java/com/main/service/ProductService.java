package com.main.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.main.entity.Product;
import com.main.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo repo;
	@Value("${path-put-images}")
	public String imgPath;

	public Product findById(long id) {
		return repo.findById(id).get();
	}

	public List<Product> allProducts() {
		return repo.findAll();
	}

	public void deleteById(long id) {
		repo.deleteById(id);
	}

	private void copyFileToLocation(InputStream in, File target) throws IOException {
		
		try (BufferedInputStream bif = new BufferedInputStream(in);
			BufferedOutputStream bof  =new BufferedOutputStream(new FileOutputStream(target));) {
			bof.write(bif.readAllBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Product insert(Product p,Part file) {
		
		String path = imgPath.concat(p.getCategory()).concat("\\");
		
		try {
			File f = new File(path);
			f.mkdirs();
			File target = new File(f, file.getSubmittedFileName());
			copyFileToLocation(file.getInputStream(), target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String finalPath = "\\images\\".concat(p.getCategory()).concat("\\").concat(file.getSubmittedFileName());
		p.setImgPath(finalPath);
		
		// return repo.save(p);
		return repo.save(p);
	}

	public Product update(Product p,Part file) {
		if (file.getSubmittedFileName() == null ||file.getSubmittedFileName().isBlank()) {
			return repo.save(p);
		}
		System.err.println("file.getSubmittedFileName() => "+file.getSubmittedFileName());
		String path = imgPath.concat(p.getCategory()).concat("\\").concat(file.getSubmittedFileName());
		System.err.println("IMG PATH => "+path);
		if(!p.getImgPath().equals(path)) {
			return insert(p, file);
		}
		return repo.save(p);
	}

	public List<Product> findByCategory(String category) {
		return repo.findByCategory(category);

	}

	public List<Product> findByName(String name) {
		return repo.findByName(name);
	}

	public List<String> findProductNames(String name){
		return repo.findProductNames(name);
	}

}
