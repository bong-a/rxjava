package com.study.rxjava.chapter2;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class L08_ReferenceTypeTest {
    public class ReferenceTypeObject{
        private String value="A";
        public void setValue(String value){
            this.value=value;
        }

        public String getValue(){
            return this.value;
        }
    }

    @Test
    public void final로_선언해도_상태변경이_가능한_예제(){
        final ReferenceTypeObject instance = new ReferenceTypeObject();
        // 참조 변경하면 컴파일 에러난다.
        //instance = new ReferenceTypeObject();

        //변경 전 확인
        Assert.assertThat(instance.getValue(),is("A"));
        // 상태 변경 A -> B
        instance.setValue("B");
        // 변경된 객체 확인
        Assert.assertThat(instance.getValue(),is("A"));
    }
}
