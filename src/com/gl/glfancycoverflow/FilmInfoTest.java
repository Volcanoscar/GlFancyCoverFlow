package com.gl.glfancycoverflow;

import java.util.ArrayList;
import java.util.List;

import com.gl.glfancycoverflow.model.FilmInfo;

/**
 * @author LittleLiByte 
 * ��������
 */
public class FilmInfoTest {

	public static String[] urls = new String[] {
			"http://img31.mtime.cn/mg/2014/09/01/093630.14934540_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/09/30/145438.41392832_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/09/09/095439.24895990_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/10/13/151034.85474901_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/09/23/084444.96794628_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/08/15/104026.33444853_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/09/26/151251.44963343_270X405X4.jpg"

	};

	public static String[] names = new String[] { "���ӻ�����", "3Dʳ�˳�", "�Ļ�·��",
			"�������", "�ƶ��Թ�", "����3", "ʷ����"

	};

	public static List<FilmInfo> getfilmInfo() {
		List<FilmInfo> filmList = new ArrayList<FilmInfo>();

		for (int i = 0; i < 7; i++) {
			FilmInfo film = new FilmInfo(names[i], urls[i], null);
			filmList.add(film);
		}
		return filmList;
	}
}
