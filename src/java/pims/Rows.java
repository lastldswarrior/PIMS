/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pims;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jared
 */
public class Rows {
    
    private List<String> list;
    
    public Rows(){
        this.list = new ArrayList();
        
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void add(String str){
        list.add(str);
    }
    
    public String getIndex(int i){
        return list.get(i);
    }
    
    
}
