package io.lx.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-27
 */
@Data
@TableName("tb_transaction")
public class TransactionEntity {

    /**
     * 
     */
    @TableId
	private String transactionId;
    /**
     * 
     */
	private String outTradeNo;
    /**
     * 
     */
	private Integer total;
    /**
     * 
     */
	private String tradeState;
    /**
     * 
     */
	private String tradeType;
    /**
     * 
     */
	private String tradeStateDesc;
    /**
     * 
     */
	private String bankType;
    /**
     * 
     */
	private String successTime;
    /**
     * 
     */
	private String payer;
    /**
     * 
     */
	private String amount;
    /**
     * 
     */
	private String sceneInfo;
    /**
     * 
     */
	private String promotionDetail;
    /**
     * 
     */
	private String failMessage;
    /**
     * 
     */
	private Date createdAt;
    /**
     * 
     */
	private Date updatedAt;
}