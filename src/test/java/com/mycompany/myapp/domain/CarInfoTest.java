package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CarInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarInfo.class);
        CarInfo carInfo1 = new CarInfo();
        carInfo1.setId(1L);
        CarInfo carInfo2 = new CarInfo();
        carInfo2.setId(carInfo1.getId());
        assertThat(carInfo1).isEqualTo(carInfo2);
        carInfo2.setId(2L);
        assertThat(carInfo1).isNotEqualTo(carInfo2);
        carInfo1.setId(null);
        assertThat(carInfo1).isNotEqualTo(carInfo2);
    }
}
