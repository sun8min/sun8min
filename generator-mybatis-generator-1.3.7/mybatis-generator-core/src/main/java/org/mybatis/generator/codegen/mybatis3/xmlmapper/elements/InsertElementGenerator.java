/**
 *    Copyright ${license.git.copyrightYears} the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.GeneratedKey;

/**
 *
 * @author Jeff Butler
 *
 */
public class InsertElementGenerator extends AbstractXmlElementGenerator {

    private boolean isSimple;

    public InsertElementGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getInsertStatementId())); //$NON-NLS-1$

        FullyQualifiedJavaType parameterType;
        if (isSimple) {
            parameterType = new FullyQualifiedJavaType(
                    introspectedTable.getBaseRecordType());
        } else {
            parameterType = introspectedTable.getRules()
                    .calculateAllFieldsClass();
        }

        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                parameterType.getFullyQualifiedName()));

        context.getCommentGenerator().addComment(answer);

        GeneratedKey gk = introspectedTable.getGeneratedKey();
        if (gk != null) {
            IntrospectedColumn introspectedColumn = introspectedTable
                    .getColumn(gk.getColumn());
            // if the column is null, then it's a configuration error. The
            // warning has already been reported
            if (introspectedColumn != null) {
                if (gk.isJdbcStandard()) {
                    answer.addAttribute(new Attribute(
                            "useGeneratedKeys", "true")); //$NON-NLS-1$ //$NON-NLS-2$
                    answer.addAttribute(new Attribute(
                            "keyProperty", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
                    answer.addAttribute(new Attribute(
                            "keyColumn", introspectedColumn.getActualColumnName())); //$NON-NLS-1$
                } else {
                    answer.addElement(getSelectKey(introspectedColumn, gk));
                }
            }
        }

        StringBuilder insertClause = new StringBuilder();

        insertClause.append("insert into "); //$NON-NLS-1$
        insertClause.append(introspectedTable
                .getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" ("); //$NON-NLS-1$
        wrapLineInsert(answer, insertClause);
        insertClause.append("<trim suffixOverrides=\",\">");
        wrapLineInsert(answer, insertClause);

        StringBuilder valuesClause = new StringBuilder();
        List<String> valuesClauses = new ArrayList<String>();
        valuesClause.append("values ("); //$NON-NLS-1$
        wrapLineValues(valuesClause, valuesClauses);
        valuesClause.append("<trim suffixOverrides=\",\">");
        wrapLineValues(valuesClause, valuesClauses);

        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn introspectedColumn = columns.get(i);

            String javaColumn = introspectedColumn.getJavaProperty(null);
            String sqlColumn = introspectedColumn.getActualColumnName();
            String valueColumn = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);

            insertClause.append(surroundWithIf(javaColumn, sqlColumn));
            valuesClause.append(surroundWithIf(javaColumn, valueColumn));

//            insertClause.append(MyBatis3FormattingUtilities
//                    .getEscapedColumnName(introspectedColumn));
//            valuesClause.append(MyBatis3FormattingUtilities
//                    .getParameterClause(introspectedColumn));
//            if (i + 1 < columns.size()) {
//                insertClause.append(", "); //$NON-NLS-1$
//                valuesClause.append(", "); //$NON-NLS-1$
//            }

            wrapLine(answer, insertClause, valuesClause, valuesClauses);
//            if (valuesClause.length() > 80) {
//                answer.addElement(new TextElement(insertClause.toString()));
//                insertClause.setLength(0);
//                OutputUtilities.xmlIndent(insertClause, 1);
//
//                valuesClauses.add(valuesClause.toString());
//                valuesClause.setLength(0);
//                OutputUtilities.xmlIndent(valuesClause, 1);
//            }
        }
        insertClause.append("</trim>");
        wrapLineInsert(answer, insertClause);
        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));

        valuesClause.append("</trim>");
        wrapLineValues(valuesClause, valuesClauses);
        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }

        if (context.getPlugins().sqlMapInsertElementGenerated(answer,
                introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    private void wrapLine(XmlElement answer, StringBuilder insertClause, StringBuilder valuesClause, List<String> valuesClauses) {
        wrapLineInsert(answer, insertClause);

        wrapLineValues(valuesClause, valuesClauses);
    }

    private void wrapLineValues(StringBuilder valuesClause, List<String> valuesClauses) {
        valuesClauses.add(valuesClause.toString());
        valuesClause.setLength(0);
        OutputUtilities.xmlIndent(valuesClause, 1);
    }

    private void wrapLineInsert(XmlElement answer, StringBuilder insertClause) {
        answer.addElement(new TextElement(insertClause.toString()));
        insertClause.setLength(0);
        OutputUtilities.xmlIndent(insertClause, 1);
    }

    private String surroundWithIf(String javaColumn, String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("  <if test=\"");
        sb.append(javaColumn);
        sb.append(" != null\">");
        sb.append(content);
        sb.append(",</if>");
        return sb.toString();
    }

}
