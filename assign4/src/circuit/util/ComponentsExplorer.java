package circuit.util;

import circuit.Component;
import circuit.Circuit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.reflect.ClassPath;

public class ComponentsExplorer {
	
	List<String> shortComponentNames;
		
	public ComponentsExplorer(){
		setComponentNames();
	}
	
	public List<String> getShortComponentNames(){
		return shortComponentNames;
	}
	

	private void setComponentNames(){
		shortComponentNames = new ArrayList<String>();

		ClassLoader classLoader = getClass().getClassLoader();
		try{
			Set<ClassPath.ClassInfo> classesInPackage = ClassPath.from(classLoader).getTopLevelClasses("circuit");
			for (ClassPath.ClassInfo clazz : classesInPackage){
				String fullName = clazz.getName();
				String shortName = clazz.getSimpleName();

				if ((fullName.contains("Gate")) && (!fullName.contains("Test"))){
					shortComponentNames.add(shortName);    
				} 
			}
		} 
		catch(Exception ex){
			System.err.println(ex.getCause() + " " + ex.getMessage());
		}
	}

}
