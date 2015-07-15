/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filearchiverclient;

import java.io.File;

/**
 *
 * @author Michal
 */
class TreeFile extends File {
    public TreeFile(File parent, String child) {
      super(parent, child);
    }
 
    public String toString() {
      return getName();
    }
  }
