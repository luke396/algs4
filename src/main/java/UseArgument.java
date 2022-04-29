public class UseArgument {
    public static void main(String[] args) {
        /*
         * 无法直接执行，需要在命令行中输入java UseArgument name，
         */
        System.out.print("Hi, "); // print 与 println 的区别在于末尾是否有换行符
        System.out.print(args[0]);
        System.out.println(". How are you?");
    }
}