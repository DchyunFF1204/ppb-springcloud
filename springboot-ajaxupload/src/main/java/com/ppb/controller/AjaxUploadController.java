package com.ppb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author daizy
 * 
 *         ajax 图片上传
 *
 */
@RestController
public class AjaxUploadController {

	@RequestMapping("/uploadAjaxImg")
	@ResponseBody
	public String uploadAjaxImg(
			@RequestParam(value = "fileData", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		// 获取文件后缀
		String fileNamePatternStr = "^.*?\\.(jpg|jpeg|png|gif|bmp)$";
		String uploadFilePath = System.getProperty("java.io.tmpdir");
		if (file == null || file.getOriginalFilename() == null
				|| "".equalsIgnoreCase(file.getOriginalFilename())) {
			return "the file is empty!";
		}
		String fileName = file.getOriginalFilename();
		if (StringUtils.isNotBlank(fileNamePatternStr)) {
			if (!Pattern.compile(fileNamePatternStr)
					.matcher(fileName.toLowerCase()).matches()) {
				return "file type is error，you can select "
						+ fileNamePatternStr + "types to send";
			}
		}

		File file1 = getFile(file, uploadFilePath);
		String url = imgUrl(fileName, file1);
		file1.delete();
		if (StringUtils.isEmpty(url)) {
			return "image upload error!";
		} else {
			return url;
		}
	}

	private File getFile(MultipartFile imgFile, String uploadImagePath) {
		String fileName = imgFile.getOriginalFilename();
		String ext = FilenameUtils.getExtension(fileName).toLowerCase();
		StringBuilder newFileName = new StringBuilder(UUID.randomUUID()
				.toString());
		newFileName = newFileName.append(RandomStringUtils
				.randomAlphanumeric(1));
		newFileName.append('.').append(ext);
		File file = this.creatFolder(uploadImagePath, newFileName.toString());
		try {
			FileUtils.copyInputStreamToFile(imgFile.getInputStream(), file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	private File creatFolder(String uploadImagePath, String fileName) {
		File file = null;
		File firstFolder = new File(uploadImagePath); // 一级文件夹
		if (!firstFolder.exists()) { // 如果一级文件夹存在，则检测二级文件夹
			firstFolder.mkdirs();
		}
		file = new File(firstFolder, fileName);
		return file;
	}

	@SuppressWarnings("resource")
	private String imgUrl(String fileOriginalName, File tmpFile) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String path = "e://img/";
		String fileName = null;
		try {
			String ext = FilenameUtils.getExtension(fileOriginalName).toLowerCase();
			fileName = sdf.format(new Date()) + "." + ext;
			File file = new File(path);
			if (!file.exists()) {
				file.mkdir();
			}
			FileInputStream is = new FileInputStream(tmpFile);
			int length = 0;
			byte[] by = new byte[1024];
			OutputStream out = new FileOutputStream(new File(path, fileName));
			while ((length = is.read(by)) != -1) {
				out.write(by, 0, length);
			}
			out.close();
			String url = "http://mobile.tingyun.tech/oss/" + fileName;
			return url;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
