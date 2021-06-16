package com.kpsec.test.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpsec.test.model.AccountResult;
import com.kpsec.test.repository.AccountRepository;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<AccountResult> getAccountByBranchCode(String branchCode){
        List<AccountResult> aa = accountRepository.getAccountByBranchCode(branchCode);
        return aa;
    }
    
    public List<Map<String, Object>> sampleApi1() {
    	return jdbcTemplate.queryForList("SELECT YEAR AS year\n"
    			+ ", (SELECT ACCOUNT_NAME\n"
    			+ "FROM ACCOUNT\n"
    			+ "WHERE ACCOUNT_NO = A.ACCOUNT_NO) AS name\n"
    			+ ", ACCOUNT_NO AS acctNo\n"
    			+ ", AMOUNT AS sumAmt\n"
    			+ "FROM (\n"
    			+ "SELECT ROW_NUMBER() OVER (PARTITION BY YEAR) AS seq\n"
    			+ ", YEAR\n"
    			+ ", ACCOUNT_NO\n"
    			+ ", AMOUNT\n"
    			+ "FROM (\n"
    			+ "SELECT SUBSTR(TRADE_DT, 0, 4) AS YEAR\n"
    			+ ", ACCOUNT_NO\n"
    			+ ", SUM(AMOUNT - CHARGE) AS AMOUNT\n"
    			+ "FROM TRADE\n"
    			+ "WHERE CANCEL_YN  <> 'Y'\n"
    			+ "GROUP BY  YEAR, ACCOUNT_NO\n"
    			+ "ORDER BY YEAR, AMOUNT DESC)\n"
    			+ "WHERE YEAR IN('2018', '2019')) A\n"
    			+ "WHERE seq = 1\n");
    }

	public List<Map<String, Object>> sampleApi2() {
		return jdbcTemplate.queryForList("SELECT \n"
				+ "'2018' AS year\n"
				+ ", ACCOUNT_NAME name\n"
				+ ", ACCOUNT_NO as acctNo\n"
				+ "FROM ACCOUNT A\n"
				+ "WHERE NOT EXISTS(SELECT 1\n"
				+ "FROM TRADE\n"
				+ "WHERE ACCOUNT_NO = A.ACCOUNT_NO\n"
				+ "AND SUBSTR(TRADE_DT, 0, 4) = '2018'\n"
				+ "AND CANCEL_YN = 'N')\n"
				+ "UNION\n"
				+ "SELECT \n"
				+ "'2019' AS year\n"
				+ ", ACCOUNT_NAME name\n"
				+ ", ACCOUNT_NO as acctNo\n"
				+ "FROM ACCOUNT A\n"
				+ "WHERE NOT EXISTS(SELECT 1\n"
				+ "FROM TRADE\n"
				+ "WHERE ACCOUNT_NO = A.ACCOUNT_NO\n"
				+ "AND SUBSTR(TRADE_DT, 0, 4) = '2019'\n"
				+ "AND CANCEL_YN = 'N')");
	}

	public List<Map<String, Object>> sampleApi3() {
		List<Map<String, Object>> tempList = jdbcTemplate.queryForList("SELECT SUBSTR(C.TRADE_DT, 0, 4) AS year\n"
				+ ", BRANCH_NM AS brName\n"
				+ ", BRANCH_CD AS brCode\n"
				+ ", SUM(C.AMOUNT - C.CHARGE) AS sumAmt\n"
				+ "FROM BRANCH  A\n"
				+ ", ACCOUNT B\n"
				+ ", TRADE C\n"
				+ "WHERE A.BRANCH_CD = B.BRANCH_CODE\n"
				+ "AND B.ACCOUNT_NO = C.ACCOUNT_NO\n"
				+ "AND C.CANCEL_YN <> 'Y'\n"
				+ "GROUP BY year,brName, brCode\n"
				+ "ORDER BY year, sumAmt desc");
				
		List resultList = new LinkedList();
		
		String year = (String) tempList.get(0).get("year");
		System.out.println("year : " + year);
		List<Map> tempList2 = new LinkedList();
	
		for(int i = 0; i < tempList.size(); i++) {
			if(!year.equals(tempList.get(i).get("year"))) {
				List addList = new LinkedList();
				
				for(Map loopMap : tempList2) {
					addList.add(loopMap);
				}
				tempList2.clear();
				
				Map tempMap = new HashMap();
				tempMap.put("year", year);
				tempMap.put("dataList", addList);
				resultList.add(tempMap);
				
				year = (String) tempList.get(i).get("year");
			}
			
			Map addMap = new HashMap();
			addMap.put("brName", tempList.get(i).get("brname"));
			addMap.put("brCode", tempList.get(i).get("brcode"));
			addMap.put("sumAmt", tempList.get(i).get("sumamt"));
			
			tempList2.add(addMap);
			
			if(i == tempList.size() - 1) {
				List addList = new LinkedList();
				
				for(Map loopMap : tempList2) {
					addList.add(loopMap);
				}
				tempList2.clear();
				
				Map tempMap = new HashMap();
				tempMap.put("year", year);
				tempMap.put("dataList", addList);
				resultList.add(tempMap);
			}
		}

		return resultList;
	}

	public Map<String, Object> sampleApi4(String brName) {
		List<Map<String, Object>> tempList = jdbcTemplate.queryForList("SELECT BRANCH_NM AS brName\n"
				+ ", BRANCH_CD AS brCode\n"
				+ ", SUM(C.AMOUNT - C.CHARGE) AS sumAmt\n"
				+ "FROM BRANCH  A\n"
				+ ", ACCOUNT B\n"
				+ ", TRADE C\n"
				+ "WHERE A.BRANCH_CD = B.BRANCH_CODE\n"
				+ "AND B.ACCOUNT_NO = C.ACCOUNT_NO\n"
				+ "AND C.CANCEL_YN <> 'Y'\n"
				+ "GROUP BY brName, brCode\n"
				+ "ORDER BY sumAmt desc");
		
		Map<String, Object> resultMap = new HashMap();
		
		Optional<Map<String, Object>> tempOp = tempList.stream()
				.filter(temp -> temp.get("brname").equals(brName))
				.findFirst();
		
		if(brName.equals("분당점")) {
			resultMap.put("code", "404");
			resultMap.put("메세지", "br code not found error");
		} else if(brName.equals("판교점")) {
			Optional<Map<String, Object>> findOp = tempList.stream()
					.filter(temp -> temp.get("brname").equals("분당점"))
					.findFirst();
			
			resultMap.put("brName", tempOp.get().get("brname"));
			resultMap.put("brCode", tempOp.get().get("brcode"));
			resultMap.put("sumAmt", Long.parseLong(String.valueOf(tempOp.get().get("sumamt"))) + Long.parseLong(String.valueOf(findOp.get().get("sumamt"))));
		} else {
			resultMap.put("brName", tempOp.get().get("brname"));
			resultMap.put("brCode", tempOp.get().get("brcode"));
			resultMap.put("sumAmt", tempOp.get().get("sumamt"));
		}
				
		return resultMap;
	}
    
}
