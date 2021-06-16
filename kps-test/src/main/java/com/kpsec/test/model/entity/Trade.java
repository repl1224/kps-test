package com.kpsec.test.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(Trade.class)
public class Trade implements Serializable {
	@Id
	private String tradeDt;
	@Id
    private String accountNo;
    @Id
    private String tradeNo;
    
    private Long amount;
    
    private Long charge;

    private String cancelYn;

}
