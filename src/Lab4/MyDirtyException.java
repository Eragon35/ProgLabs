package Lab4;

public class MyDirtyException extends RuntimeException {
    private String throwedClass;
    private String fileName;
    private String methodName;
    private int lineNum;
    private Object obj;

    public MyDirtyException(){
        throwedClass = this.getStackTrace()[1].getClassName();
        fileName = this.getStackTrace()[1].getFileName();
        methodName = this.getStackTrace()[1].getMethodName();
        lineNum = this.getStackTrace()[1].getLineNumber();
    }
    public MyDirtyException(Object obj){
        obj = this.obj;
    }
    public MyDirtyException(String msg){
        super(msg);
        throwedClass = this.getStackTrace()[1].getClassName();
        fileName = this.getStackTrace()[1].getFileName();
        methodName = this.getStackTrace()[1].getMethodName();
        lineNum = this.getStackTrace()[1].getLineNumber();
    }

    @Override
    public String getMessage(){
        return super.getMessage();
    }
    public String getThrowedClass(){
        return throwedClass;
    }
    public String getFileName(){
        return fileName;
    }
    public String getMethodName(){
        return  methodName;
    }
    public int getLineNum(){
        return lineNum;
    }
    public Object getObj(){
        return obj;
    }
}
