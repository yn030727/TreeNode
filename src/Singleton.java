public class Singleton {
    private static Singleton instance;
    //为了禁止外部创建Singleton的实例，我们用private关键字将其构造函数进行了私有
    private Singleton(){}
    //给外部提供了一个getInstance()静态方法来获取Singleton实例。
    public synchronized static Singleton getInstance(){//synchronized同步锁
        if(instance==null){
            instance=new Singleton();
        }
        //一直只有一个instance
        return instance;
    }
    public void singletonTest(){
        System.out.println("singletonTest is called");
    }
}
