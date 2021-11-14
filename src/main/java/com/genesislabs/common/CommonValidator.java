package com.genesislabs.common;

import com.genesislabs.common.exception.BadRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

@Log4j2
@Component
public class CommonValidator {

    /**
     * Map객체를 DTO객체로 변환한다.
     * @param _dto  변환될 dto
     * @param _dataMap  클라이이언트 요청 parameter
     */
    public void convertMapToDto(Object _dto, Map _dataMap){
        Iterator itr = _dataMap.keySet().iterator();
        String keyAttribute = null;
        String keyPreAttr = null;
        String setMethodStr = "set";
        String methodString = null;     //setter method namming

        while( itr.hasNext() ) {
            Method method = null;
            keyAttribute = (String) itr.next();
            keyPreAttr = (String) keyAttribute;
            methodString = setMethodStr
                    + keyPreAttr.substring(0, 1).toUpperCase()
                    + keyPreAttr.substring(1);

            try {
                method = _dto.getClass().getDeclaredMethod(methodString, String.class);
            } catch (NoSuchMethodException e1) {
                log.error("not found param:{}", keyPreAttr);
            } catch (Exception e) {
                log.error(e);
            }

            // _dataMap conver to vo.
            if ( method != null ) {
                try {
                    method.invoke( _dto, _dataMap.get( keyAttribute ) );
                } catch ( IllegalAccessException e ) {
                    log.error(e);
                } catch ( IllegalArgumentException e ) {
                    log.error(e);
                } catch ( InvocationTargetException e ) {
                    log.error(e);
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * 요청된 사용자의 parameter타입을 검증한다.
     * @param _bodyMap 클라이언트 요청 parameter
     */
    public void paramTypeCheck(Map _bodyMap) {
        for(Object key : _bodyMap.keySet()){
            if(!(_bodyMap.get(key) instanceof String))
                throw new BadRequestException(key + " 은(는) 문자열 입니다", "0000");
        }
    }

}
