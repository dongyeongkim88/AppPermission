package com.example.deathquin.apppermission

import android.content.Context
import android.graphics.Typeface
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class ExpandaListAdapter(val context: Context, val listOfHeaderData: List<String>, val listOfChildData: HashMap<String, List<String>>, val profilingTable: HashMap<String, HashMap<String, Boolean>>) : BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return listOfHeaderData[groupPosition]
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasStableIds(): Boolean {
        return false
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val headerTitle = getGroup(groupPosition) as String

        val view: View = LayoutInflater.from(context).inflate(R.layout.list_header, parent, false)

        val listHeaderText = view.findViewById<AppCompatTextView>(R.id.list_header_text) as AppCompatTextView

        listHeaderText.setTypeface(null, Typeface.BOLD)
        listHeaderText.text = headerTitle

        return view

    }

    override fun getChildrenCount(groupPosition: Int): Int {

        return listOfChildData[listOfHeaderData[groupPosition]]!!.size
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {

        return listOfChildData[listOfHeaderData[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        val childText = getChild(groupPosition, childPosition) as String
        val headerTitle = getGroup(groupPosition) as String
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val listItemText = view.findViewById<AppCompatTextView>(R.id.list_item_text) as AppCompatTextView
        val listResultText = view.findViewById<AppCompatTextView>(R.id.result) as AppCompatTextView

        val resultValue = profilingTable[headerTitle]!![childText]

        //Log.i("aaaa", "$headerTitle $childText  ->  $asd")
        listItemText.text = childText
        listResultText.text = resultValue.toString()

        return view

    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGroupCount(): Int {
        return listOfHeaderData.size
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}







/*
init {
    this.context = context
}

override fun getGroupCount(): Int {
    return listGroup.size
}

override fun getChildrenCount(groupPosition: Int): Int {
    return 1
}

override fun getGroup(groupPosition: Int): Any {
    return listGroup[groupPosition]
}

override fun getChild(groupPosition: Int, childPosition: Int): Any? {
    return null
}

override fun getGroupId(groupPosition: Int): Long {
    return groupPosition.toLong()
}

override fun getChildId(groupPosition: Int, childPosition: Int): Long {
    return childPosition.toLong()
}

override fun hasStableIds(): Boolean {
    return false
}


override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {

    var convertView = convertView
    if (convertView == null) {
        val infalInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        convertView = infalInflater.inflate(R.layout.group_layout, null)
    }

    (convertView as TextView).text = getGroup(groupPosition) as String

    return convertView
}


override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {

    var convertView = convertView

    if (convertView == null) {
        val infalInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemType = getChildType(groupPosition, childPosition)

    }

    return convertView as View

}

override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
    return false
}

override fun getChildTypeCount(): Int {
    return 11
}

override fun getChildType(groupPosition: Int, childPosition: Int): Int {
    return groupPosition
}*/
