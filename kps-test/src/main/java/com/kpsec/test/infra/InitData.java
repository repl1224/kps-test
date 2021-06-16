package com.kpsec.test.infra;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kpsec.test.model.entity.Account;
import com.kpsec.test.model.entity.Branch;
import com.kpsec.test.model.entity.Trade;
import com.kpsec.test.repository.AccountRepository;
import com.kpsec.test.repository.BranchRepository;
import com.kpsec.test.repository.TradeRepository;

@Component
public class InitData {

    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    TradeRepository tradeRepository;
    
    @Autowired
    BranchRepository branchRepository;
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initAccount() throws IOException {
    	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! PostConstruct");
    	
        if (accountRepository.count() == 0) {
            Resource resource1 = new ClassPathResource("과제1_데이터_계좌정보.csv");
            List<Account> accountList = Files.readAllLines(resource1.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split(",");
                        return Account.builder().accountNo(split[0]).accountName(split[1]).branchCode(split[2])
                                .build();
                    }).collect(Collectors.toList());
            accountRepository.saveAll(accountList);
        }
        
        if (tradeRepository.count() == 0) {
            Resource resource2 = new ClassPathResource("과제1_데이터_거래내역.csv");
            List<Trade> tradeList = Files.readAllLines(resource2.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split(",");
                        return Trade.builder()
                        		.tradeDt(split[0])
                        		.accountNo(split[1])
                        		.tradeNo(split[2])
                        		.amount(Long.parseLong(split[3]))
                        		.charge(Long.parseLong(split[4]))
                        		.cancelYn(split[5])
                        		.build();
                    }).collect(Collectors.toList());
            
            System.out.println("!!!!!!!!!!!!!");
            System.out.println(tradeList.size());
            tradeRepository.saveAll(tradeList);
        }
        
        if (branchRepository.count() == 0) {
            Resource resource3 = new ClassPathResource("과제1_데이터_관리점정보.csv");
            List<Branch> branchList = Files.readAllLines(resource3.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split(",");
                        return Branch.builder()
                        		.branchCd(split[0])
                        		.branchNm(split[1])
                        		.build();
                    }).collect(Collectors.toList());
            branchRepository.saveAll(branchList);
        }
    }
}
