package graphicView;

import javafx.fxml.Initializable;

public abstract class PageController implements Initializable {
    private Page page;


    /*protected boolean send(Message req) {
        //this function will send a request to controller and receive the answer
        try {
            Controller.process(req);
            return true;
        } catch (Exception e) {
            processError(e);
            return false;
        }
    }//end send*/

    //this function will use to process the exception which controller send as ansewr
    public abstract void processError(Exception e);

    //this function will use to clear scenes
    public abstract void clearPage();

    //this function will update scene objects
    public abstract void update();

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
