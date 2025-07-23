package com.gexw.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author gexw
 * @since 2025-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Members implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String age;

    private String addr;

    private String email;


}
