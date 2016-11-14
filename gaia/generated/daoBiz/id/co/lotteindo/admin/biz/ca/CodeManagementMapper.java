/* Copyright (C) Indo Lotte Makmur, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited Proprietary and confidential
 * 
 * Written by ECM proejct team
 */
package id.co.lotteindo.admin.biz.ca;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import id.co.lotteindo.admin.entity.biz.ca.TcaCommCdEVO;

 /**
 * {프로그램 코드} : 클래스 정의
 * CreatedDate : 2016.11.13
 */
@Repository
@Lazy
public interface TcaCommCdMapper {
    /**
     * 작성자:s911675.
     * 개요:get tcaCommCdEVO.
     */
	public TcaCommCdEVO get(TcaCommCdEVO tcaCommCdEVO) throws Exception;
	
    /**
     * 작성자:s911675.
     * 개요:get tcaCommCdEVO list.
     */
	public List<TcaCommCdEVO> getList(TcaCommCdEVO tcaCommCdEVO) throws Exception;

    /**
     * 작성자:s911675.
     * 개요:get tcaCommCdEVO total count.
     */
	public int getTotalCount() throws Exception;
}