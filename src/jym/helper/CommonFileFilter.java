// CatfoOD 2009-9-3 ����07:45:46

package jym.helper;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;


/**
 * ͨ���ļ���չ��������
 * v0.2
 */
public class CommonFileFilter implements FileFilter {
	/**
	 * ����ģʽ��ֻ��ָ������չ������ͨ��
	 */
	public static final FilterModel INCLUDE_MODEL = new FilterModel("include", false);
	/**
	 * �ų�ģʽ��ָ������չ����ֹͨ��
	 */
	public static final FilterModel EXCLUDE_MODEL = new FilterModel("exclude", true);
	
	private ArrayList<String> list;
	private FilterModel model;
	private boolean dir = false;
	
	
	/**
	 * ����һ��'����ģʽ'�ļ���������Ĭ���ļ����޷�ͨ��<br>
	 * ��add()��չ��֮ǰ���κ��ļ����޷�ͨ��
	 */
	public CommonFileFilter() {
		list = new ArrayList<String>();
		model = INCLUDE_MODEL;
	}
	
	/**
	 * �����ļ��������Ĺ���ģʽ���������ֶ�<br>
	 * ע�⣺��������ģʽ�󣬼�ס������Ҫ�����չ���б�
	 * @param model - ����ģʽ
	 */
	public void setModel(FilterModel model) {
		if (model!=null) {
			this.model = model;
		}
	}
	
	/**
	 * �����ļ����Ƿ�����ͨ����Ĭ�Ͻ�ֹͨ��
	 * �ļ�����������������չ�����
	 */
	public void dirCanAccept(boolean dir) {
		this.dir = dir;
	}
	
	/**
	 * ��ָ������չ������ģ���б�
	 * @param exname - ��'.'��ʼ���ļ���չ�������û����'.'��ʼ���Զ����'.'
	 */
	public void add(String exname) {
		exname = check(exname);
		list.add(exname);
	}
	
	/**
	 * �Ƴ�ָ������չ���������չ���������б��У���ʲô������
	 * @param exname - ��'.'��ʼ���ļ���չ�������û����'.'��ʼ���Զ����'.'
	 */
	public void remove(String exname) {
		exname = check(exname);
		list.remove(exname);
	}
	
	/**
	 * ���ȫ��ָ������չ��
	 */
	public void removeAll() {
		list.clear();
	}
	
	/**
	 * �Զ����'.'
	 */
	private String check(String name) {
		if (name!=null && name.length()>0) {
			if (!name.startsWith(".")) {
				name = "." + name;
			}
		}
		return name;
	}

	@Override
	public boolean accept(File file) {
		if (!dir && file.isDirectory()) return false;
		else if (file.isDirectory()) return true;
		
		boolean accept = model.getMode();
		
		String name = file.getName();
		for (int i=0; i<list.size(); ++i) {
			if ( name.endsWith(list.get(i)) ) {
				accept = !accept;
				break;
			}
		}
		return accept;
	}
	
	/**
	 * ����FilenameFilter�ı�ݷ�������Ϊ��FileFilter��ͬ<br>
	 * ע���CommonFileFilter���޸�ֱ��Ӱ�췵�ص�FilenameFilter<br>
	 * ���ص�FilenameFilter������ԱȽ����Ƿ���false
	 */
	public FilenameFilter getFilenameFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				dir = new File(dir, name);
				return CommonFileFilter.this.accept(dir);
			}
		};
	}

	/**
	 * ������ģ�Ͱ�ȫö��
	 */
	private static class FilterModel {
		private String _name;
		private boolean _mode;
		private FilterModel(String name, boolean mode) {
			_name = name;
			_mode = mode;
		};
		public String toString() { return _name; }
		public boolean getMode() { return _mode; }
	}
}
