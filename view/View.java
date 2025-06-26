package view;

public interface View
{
	//prototypes to be implemented
	void show(String msg);
	
	String prompt(String msg);
	int promptInt(String msg);
	int promptIntRange(String msg, int min, int max);
}