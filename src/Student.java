public class Student {
    private String StudID;
    private String StudName;
    private Module[] modules = new Module[3] ;
    public Student(String StudID , String StudName)
    {
        this.StudID=StudID;
        this.StudName=StudName;
        for (int i = 0; i < modules.length; i++) {
            modules[i] = new Module();
        }
    }
    public String getStudID() {
        return StudID;
    }
    public String getStudName() {
        return StudName;
    }
    public void setStudName(String StudName) {
        this.StudName = StudName;
    }
    public Module[] getModules() {
        return modules;
    }
    public int getTotalMarks() {
        int total = 0;
        for (Module module : modules) {
            total += module.getModuleMarks();
        }
        return total;
    }
    public int getAverageMarks() {
        return getTotalMarks() / modules.length;
    }
    public String getModuleGrade() {
        double average = getAverageMarks();
        if (average >= 80)
        {
            return "Distinction";
        }
        else if (average >= 70)
        {
            return "Merit";
        }
        else if (average >= 40)
        {
            return "Pass";
        }
        else
        {
            return "Fail";
        }
    }
}