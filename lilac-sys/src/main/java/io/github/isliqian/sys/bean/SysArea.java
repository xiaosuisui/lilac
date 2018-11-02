
package io.github.isliqian.sys.bean;


import io.github.isliqian.utils.base.DataEntity;
import io.github.isliqian.utils.base.TreeEntity;
import lombok.Data;

/**
 * 区域Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
@Data
public class SysArea extends DataEntity<SysArea> {

	private static final long serialVersionUID = 1L;
	private String parentIds; // 所有父级编号
	private String code; 	// 区域编码
	private String name; 	// 区域名称
	private Integer sort;		// 排序
	private String type; 	// 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）




}