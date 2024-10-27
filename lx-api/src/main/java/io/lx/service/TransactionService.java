package io.lx.service;

import io.lx.common.service.CrudService;
import io.lx.dto.TransactionDTO;
import io.lx.entity.TransactionEntity;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-27
 */
public interface TransactionService extends CrudService<TransactionEntity, TransactionDTO> {

    /**
     * 写入交易流水表
     */
    void insertTrans(TransactionDTO transactionDTO) throws Exception;

}