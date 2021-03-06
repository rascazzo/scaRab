<?xml version="1.0" encoding="UTF-8"?>
<p:pipeline xmlns:p="http://www.w3.org/ns/xproc"
            name="xinclude-and-validate"
            version="1.0">
  <p:input port="schemas" sequence="true"/>

  <p:xinclude/>

  <p:validate-with-xml-schema>
    <p:input port="schema">
      <p:pipe step="xinclude-and-validate" port="schemas"/>
    </p:input>
  </p:validate-with-xml-schema>
</p:pipeline>