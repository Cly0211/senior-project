package com.seniorProject.project.service;

import com.seniorProject.project.mapper.VerificationMapper;
import com.seniorProject.project.model.User;
import com.seniorProject.project.model.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class VerificationService {
    @Autowired
    VerificationMapper verificationMapper;

    /**
     * delete odd code and add new code
     */
    @Transactional
    public void addCode(String id, String verCode, Date expiredDate){
        verificationMapper.delete(id, expiredDate);
        verificationMapper.insert(id, verCode, expiredDate);
    }


    /**
     * verify code
     */
    public Integer verify(String id, String verCode, Date date){
        Verification dbVer = verificationMapper.selectCode(id);
        if (!verCode.equals(dbVer.getVerCode()))
            return -1;
        if (date.compareTo(dbVer.getExpiredDate()) > 0)
            return -2;
        return 1;
    }
}
