package com.example.demo.eto;

import lombok.Data;

@Data
public class ResponseETO {
    private long projectId;
    private boolean validInput;

    public ResponseETO(long projectId, boolean validInput){

        this.projectId = projectId;
        this.validInput = validInput;
    }


}
