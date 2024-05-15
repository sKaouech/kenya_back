package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonInfo.class);
        PersonInfo personInfo1 = new PersonInfo();
        personInfo1.setId(1L);
        PersonInfo personInfo2 = new PersonInfo();
        personInfo2.setId(personInfo1.getId());
        assertThat(personInfo1).isEqualTo(personInfo2);
        personInfo2.setId(2L);
        assertThat(personInfo1).isNotEqualTo(personInfo2);
        personInfo1.setId(null);
        assertThat(personInfo1).isNotEqualTo(personInfo2);
    }
}
