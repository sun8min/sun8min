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

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class UpdateByPrimaryKeyWithoutBLOBsElementGenerator extends
        AbstractXmlElementGenerator {

    private boolean isSimple;

    public UpdateByPrimaryKeyWithoutBLOBsElementGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getUpdateByPrimaryKeyStatementId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                introspectedTable.getBaseRecordType()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" set "); //$NON-NLS-1$
        answer.addElement(new TextElement(sb.toString()));

        // set up for first column
        sb.setLength(0);
        sb.append("<trim suffixOverrides=\",\">");
        answer.addElement(new TextElement(sb.toString()));
        sb.setLength(0);
        OutputUtilities.xmlIndent(sb, 1);

        Iterator<IntrospectedColumn> iter;
        if (isSimple) {
            // 包括主键
            iter = ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getAllColumns()).iterator();
            // update的set字段不包括主键
//            iter = ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns()).iterator();
        } else {
            iter = ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getBaseColumns()).iterator();
        }
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();

            String javaColumn = introspectedColumn.getJavaProperty(null);
            String sqlColumn = introspectedColumn.getActualColumnName();
            String valueColumn = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn);

            String surroundWithIf = generateSurroundWithIf(javaColumn, sqlColumn, valueColumn);
            sb.append(surroundWithIf);
//            sb.append(MyBatis3FormattingUtilities
//                    .getEscapedColumnName(introspectedColumn));
//            sb.append(" = "); //$NON-NLS-1$
//            sb.append(MyBatis3FormattingUtilities
//                    .getParameterClause(introspectedColumn));

//            if (iter.hasNext()) {
//                sb.append(',');
//            }

            answer.addElement(new TextElement(sb.toString()));

            // set up for the next column
            if (iter.hasNext()) {
                sb.setLength(0);
                OutputUtilities.xmlIndent(sb, 1);
            }
        }
        sb.setLength(0);
        sb.append("</trim>");
        answer.addElement(new TextElement(sb.toString()));
        sb.setLength(0);
        OutputUtilities.xmlIndent(sb, 1);
        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append("where "); //$NON-NLS-1$
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins()
                .sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    private String generateSurroundWithIf(String javaColumn, String sqlColumn, String valueColumn) {
        StringBuilder sb = new StringBuilder();
        sb.append("<if test=\"");
        sb.append(javaColumn);
        sb.append(" != null\">");
        sb.append(sqlColumn);
        sb.append(" = ");
        sb.append(valueColumn);
        sb.append(",</if>");
        return sb.toString();
    }
}
