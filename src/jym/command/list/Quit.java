// CatfoOD 2009-9-18 ����11:40:29 
package jym.command.list;

import jym.command.ICommander;
import jym.command.IContainer;

public class Quit implements ICommander {

	@Override
	public boolean doit(IContainer d, String[] args) {
		d.exit();
		return true;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public String getHelp() {
		return "�˳�";
	}

	@Override
	public String getInfo() {
		return getHelp();
	}

}
