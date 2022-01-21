package fg331.free.bg;

public class Main {

    Display display;
    int windowWidth = 640;
    int windowHeight = 360;

    public static void main(String[] args) {

    new Main().create();
    }


    private void create(){

        display = new Display("Библиотека", windowWidth,windowHeight);


    }
}
